package com.event.serviceImpl;

import com.event.dao.CategoryDao;
import com.event.dao.ProductDao;
import com.event.service.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final CategoryDao categoryDao;

    private final ProductDao productDao;

    public DashboardServiceImpl(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("category", categoryDao.count());
        map.put("product", productDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
