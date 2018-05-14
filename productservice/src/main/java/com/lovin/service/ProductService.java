package com.lovin.service;

import com.lovin.IProductService;
import com.lovin.bean.Product;

/**
 * Created by bixin on 2018/4/13.
 */
public class ProductService implements IProductService {

    public Product queryById(long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("Water");
        product.setPrice(1.0);
        return product;
    }
}
