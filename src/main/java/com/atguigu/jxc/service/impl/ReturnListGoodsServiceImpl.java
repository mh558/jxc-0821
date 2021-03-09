package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.*;
import com.atguigu.jxc.entity.*;
import com.atguigu.jxc.exception.UntifyException;
import com.atguigu.jxc.service.*;
import com.atguigu.jxc.vo.PurListVo;
import com.atguigu.jxc.vo.PurchaseListGoodsVo;
import com.atguigu.jxc.vo.ReturnGoodsVo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReturnListGoodsServiceImpl implements ReturnListGoodsService {

    @Autowired
    private ReturnListGoodsDao returnListGoodsDao;
    @Autowired
    private ReturnListService returnListService;
    @Autowired
    private LogService  logService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsTypeService goodsTypeService;

    private final static Gson GSON=new Gson();


    @Override
    @Transactional
    public void saveReturnListGoods(String returnNumber, ReturnList returnList, String returnListGoodsStr, HttpSession session) {

        User currentUser = (User) session.getAttribute("currentUser");
        if(currentUser==null || StringUtils.isEmpty(returnListGoodsStr) || StringUtils.isEmpty(returnNumber) ){
           throw new UntifyException("未登录或非法请求");
        }
        returnList.setReturnNumber(returnNumber);
        returnList.setUserId(currentUser.getUserId());
        this.returnListService.saveReturnList(returnList);

        List<ReturnListGoods> returnListGoods = GSON.fromJson(returnListGoodsStr, new TypeToken<List<ReturnListGoods>>() {
        }.getType());
        if(!CollectionUtils.isEmpty(returnListGoods)){
            this.returnListGoodsDao.saveReturnListGoods(returnListGoods.stream().map(t->{
                t.setReturnListId(returnList.getReturnListId());
                return t;
            }).collect(Collectors.toList()));
        }
        this.logService.save(new Log(Log.INSERT_ACTION,"退货单保存"));


    }

    @Override
    public List<ReturnList> list(ReturnGoodsVo returnGoodsVo) {
      List<ReturnList> returnLists=  this.returnListService.list(returnGoodsVo);
        this.logService.save(new Log(Log.SELECT_ACTION,"退货单查询"));
        if(CollectionUtils.isEmpty(returnLists)){
          return null;
        }
        return returnLists.stream().map(t -> {
            t.setSupplierName(this.supplierService.getSupplierById(t.getSupplierId()).getSupplierName());
            t.setTrueName(this.userService.getUserById(t.getUserId()).getTrueName());
            return t;
        }).collect(Collectors.toList());


    }

    @Override
    public List<ReturnListGoods> goodsList(Integer returnListId) {
        if(StringUtils.isEmpty(returnListId)){
            return null;
        }
        this.logService.save(new Log(Log.SELECT_ACTION,"退货单具体商品查询"));
        return this.returnListGoodsDao.goodsList(returnListId);
    }

    @Override
    @Transactional
    public void deleteReturnList(Integer returnListId) {
        if(StringUtils.isEmpty(returnListId)){
            throw new UntifyException("请选择要删除的退货单");
        }
        Integer flag=  this.returnListGoodsDao.deleteReturnList(returnListId);
        if(flag>0){
            this.returnListService.deleteReturnList(returnListId);
        }
        this.logService.save(new Log(Log.DELETE_ACTION,"退货单删除"));
    }

    @Override
    public String count(PurListVo purListVo) {
        String s=null;
        ReturnGoodsVo returnGoodsVo = new ReturnGoodsVo();
        returnGoodsVo.setETime(purListVo.getETime());
        returnGoodsVo.setSTime(purListVo.getSTime());
        List<ReturnList> list = this.returnListService.list(returnGoodsVo);
        if(!CollectionUtils.isEmpty(list)){

            List<Integer> collect = list.stream().map(ReturnList :: getReturnListId).collect(Collectors.toList());
            List<ReturnListGoods> count = this.returnListGoodsDao.count(collect, purListVo);
            if(CollectionUtils.isEmpty(collect)){
                return null;
            }

            s = GSON.toJson(count.stream().map(purchaseListGoods -> {

                PurchaseListGoodsVo purchaseGoodsVo = new PurchaseListGoodsVo();
                //基本数据
                purchaseGoodsVo.setCode(purchaseListGoods.getGoodsCode());
                purchaseGoodsVo.setModel(purchaseListGoods.getGoodsModel());
                purchaseGoodsVo.setName(purchaseListGoods.getGoodsName());
                purchaseGoodsVo.setNum(purchaseListGoods.getGoodsNum());
                purchaseGoodsVo.setPrice(purchaseListGoods.getPrice());
                purchaseGoodsVo.setTotal(purchaseListGoods.getTotal());
                purchaseGoodsVo.setUnit(purchaseListGoods.getGoodsUnit());

                //退货单数据查询
                ReturnList byId = this.returnListService.getById(purchaseListGoods.getReturnListId());
                if (byId != null) {
                    purchaseGoodsVo.setDate(byId.getReturnDate());
                    purchaseGoodsVo.setNumber(byId.getReturnNumber());
                    Supplier supplierById = this.supplierService.getSupplierById(byId.getSupplierId());
                    if (supplierById != null) {
                        purchaseGoodsVo.setSupplierName(supplierById.getSupplierName());
                    }
                }

                //退货商品类型查询
                GoodsType goodsTypeById = this.goodsTypeService.getGoodsTypeById(purchaseListGoods.getGoodsTypeId());
                if (goodsTypeById != null) {
                    purchaseGoodsVo.setGoodsType(goodsTypeById.getGoodsTypeName());
                }
                return purchaseGoodsVo;
            }).collect(Collectors.toList()));

        }
        this.logService.save(new Log(Log.SELECT_ACTION,"退货单统计"));
        return s;
    }

    @Override
    public List<ReturnListGoods> listByDay(List<Integer> collect) {
        return this.returnListGoodsDao.count(collect, null);
    }
}