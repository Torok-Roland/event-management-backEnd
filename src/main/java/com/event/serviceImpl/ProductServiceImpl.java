package com.event.serviceImpl;

import com.event.JWT.JwtFilter;
import com.event.constents.EventConstants;
import com.event.dao.ProductDao;
import com.event.model.Category;
import com.event.model.Product;
import com.event.service.ProductService;
import com.event.utils.EventUtils;
import com.event.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// aici se intampla logica din metoda
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    private final JwtFilter jwtFilter;

    public ProductServiceImpl(ProductDao productDao, JwtFilter jwtFilter) {
        this.productDao = productDao;
        this.jwtFilter = jwtFilter;
    }

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, false)) {
                    productDao.save(getProductFromMap(requestMap, false));
                    return EventUtils.getResponseEntity("Product Added Successfully", HttpStatus.OK);
                }
                return EventUtils.getResponseEntity(EventConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

            } else {
                return EventUtils.getResponseEntity(EventConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id")) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if (isAdd) {
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setLocation(requestMap.get("location"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;

    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {
            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, true)) {
                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        Product product = getProductFromMap(requestMap, true);
                        product.setStatus(optional.get().getStatus());
                        productDao.save(product);
                        return EventUtils.getResponseEntity("Product Updated Successfully", HttpStatus.OK);
                    } else {
                        return EventUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
                    }
                } else {
                    return EventUtils.getResponseEntity(EventConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return EventUtils.getResponseEntity(EventConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional optional = productDao.findById(id);
                if (!optional.isEmpty()) {
                    productDao.deleteById(id);
                    return EventUtils.getResponseEntity("Product Deleted Successfully!", HttpStatus.OK);
                } else {
                    return EventUtils.getResponseEntity("Product id does not exist!", HttpStatus.OK);
                }
            } else {
                return EventUtils.getResponseEntity(EventConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Modifying
    @Transactional
    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {
                    productDao.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return EventUtils.getResponseEntity("Product Status Updated Successfully!", HttpStatus.OK);
                } else {
                    return EventUtils.getResponseEntity("Product id does not exist!", HttpStatus.OK);
                }
            } else {
                return EventUtils.getResponseEntity(EventConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getProductByCategory(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getProductById(id),HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
