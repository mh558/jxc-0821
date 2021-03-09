package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.*;
import com.atguigu.jxc.entity.*;
import com.atguigu.jxc.exception.UntifyException;
import com.atguigu.jxc.service.*;
import com.atguigu.jxc.vo.PurListVo;
import com.atguigu.jxc.vo.PurchaseGoodsVo;
import com.atguigu.jxc.vo.PurchaseListGoodsVo;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseListGoodsServiceImpl implements PurchaseListGoodsService {

    @Autowired
    private PurchaseListGoodsDao purchaseListGoodsDao;
    @Autowired
    private LogService logService;
    @Autowired
    private PurchaseListService purchaseListService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private UserService  userService;
    @Autowired
    private GoodsTypeService  goodsTypeService;

    private final static Gson GSON=new Gson();

    @Override
    @Transactional
    public void save(PurchaseList purchaseList, String purchaseNumber, String purchaseListGoodsStr, HttpSession session) {


        User user = (User) session.getAttribute("currentUser");
        purchaseList.setPurchaseNumber(purchaseNumber);
        purchaseList.setUserId(user.getUserId());
        this.purchaseListService.save(purchaseList);


      //添加商品列表
        if(!StringUtils.isEmpty(purchaseListGoodsStr)){
            List<PurchaseListGoods> purchaseListGoodsList = GSON.fromJson(purchaseListGoodsStr, new TypeToken<List<PurchaseListGoods>>()                      {
            }.getType());
          if(!CollectionUtils.isEmpty(purchaseListGoodsList)){
              this.purchaseListGoodsDao.save( purchaseListGoodsList.stream().map(t -> {
                  t.setPurchaseListId(purchaseList.getPurchaseListId());
                  return t;
              }).collect(Collectors.toList()));
          }
            this.logService.save(new Log(Log.INSERT_ACTION,"进货单保存"));
        }

    }

    @Override
    public List<PurchaseList> list(PurchaseGoodsVo goodsVo) {

        List<PurchaseList> list = this.purchaseListService.list(goodsVo);
        this.logService.save(new Log(Log.SELECT_ACTION,"进货单查询"));
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        List<PurchaseList> collect = list.stream().map(t -> {
            Supplier supplierById = this.supplierService.getSupplierById(t.getSupplierId());
            t.setSupplierName(supplierById.getSupplierName());
            t.setTrueName(this.userService.getUserById(t.getUserId()).getTrueName());
            return t;
        }).collect(Collectors.toList());
        return collect;

    }

    @Override
    public List<PurchaseListGoods> goodsList(Integer purchaseListId) {
        if(purchaseListId==null){
            return null;
        }
        this.logService.save(new Log(Log.SELECT_ACTION,"进货单商品列表查询"));
       return this.purchaseListGoodsDao.goodsList(purchaseListId);

    }

    @Override
    @Transactional
    public void deletePurchaseList(Integer purchaseListId) {
        if(purchaseListId==null) {
            return;
        }
        Integer flag = this.purchaseListGoodsDao.deletePurchaseList(purchaseListId);
        if(flag>0){
            this.purchaseListService.deletePurchaseList(purchaseListId);
        }
        this.logService.save(new Log(Log.DELETE_ACTION,"进货单删除"));


    }

    @Override
    public void updateState(Integer purchaseListId) {
        if(purchaseListId==null){
            throw new UntifyException("非法参数");
        }
        PurchaseList purchaseList = new PurchaseList();
        purchaseList.setPurchaseListId(purchaseListId);
        this.purchaseListService.updateState(purchaseList);
        this.logService.save(new Log(Log.UPDATE_ACTION,"进货单状态修改"));

    }

    @Override
    public String count(PurListVo purListVo) {
        String s=null;
        PurchaseGoodsVo goodsVo = new PurchaseGoodsVo();
        goodsVo.setETime(purListVo.getETime());
        goodsVo.setSTime(purListVo.getSTime());
        List<PurchaseList> list = this.purchaseListService.list(goodsVo);
        if(!CollectionUtils.isEmpty(list)){
            List<Integer> collect = list.stream().map(PurchaseList :: getPurchaseListId).collect(Collectors.toList());
            List<PurchaseListGoods> count = this.purchaseListGoodsDao.count(collect, purListVo);
            if(CollectionUtils.isEmpty(count)){
                return null;
            }
            s = GSON.toJson(count.stream().map(purchaseListGoods -> {

                PurchaseListGoodsVo purchaseGoodsVo = new PurchaseListGoodsVo();

                purchaseGoodsVo.setCode(purchaseListGoods.getGoodsCode());
                purchaseGoodsVo.setModel(purchaseListGoods.getGoodsModel());
                purchaseGoodsVo.setName(purchaseListGoods.getGoodsName());
                purchaseGoodsVo.setNum(purchaseListGoods.getGoodsNum());
                purchaseGoodsVo.setPrice(purchaseListGoods.getPrice());
                purchaseGoodsVo.setTotal(purchaseListGoods.getTotal());
                purchaseGoodsVo.setUnit(purchaseListGoods.getGoodsUnit());

                //进货单数据查询
                PurchaseList byId = this.purchaseListService.getById(purchaseListGoods.getPurchaseListId());
                if (byId != null) {
                    purchaseGoodsVo.setDate(byId.getPurchaseDate());
                    purchaseGoodsVo.setNumber(byId.getPurchaseNumber());
                    Supplier supplierById = this.supplierService.getSupplierById(byId.getSupplierId());
                    if (supplierById != null) {
                        purchaseGoodsVo.setSupplierName(supplierById.getSupplierName());
                    }
                }

                //进货单类型查询
                GoodsType goodsTypeById = this.goodsTypeService.getGoodsTypeById(purchaseListGoods.getGoodsTypeId());
                if (goodsTypeById != null) {
                    purchaseGoodsVo.setGoodsType(goodsTypeById.getGoodsTypeName());
                }
                return purchaseGoodsVo;
            }).collect(Collectors.toList()));

        }
        this.logService.save(new Log(Log.SELECT_ACTION,"进货单统计"));
        return s;
    }

    @Override
    public Integer listByDay(List<Integer> psId) {
        return this.purchaseListGoodsDao.listByDay(psId);
    }

    @Override
    public List<PurchaseListGoods> queryListByPsId(List<Integer> psId) {
        return this.purchaseListGoodsDao.count(psId, new PurListVo());
    }
}