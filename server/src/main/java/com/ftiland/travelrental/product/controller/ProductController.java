package com.ftiland.travelrental.product.controller;

import com.ftiland.travelrental.common.utils.pagination.PageResponseDto;
import com.ftiland.travelrental.image.service.ImageService;
import com.ftiland.travelrental.product.dto.*;
import com.ftiland.travelrental.product.entity.Product;
import com.ftiland.travelrental.product.mapper.ProductMapper;
import com.ftiland.travelrental.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<CreateProduct.Response> createProduct(
            @Valid @RequestBody CreateProduct.Request request
            /*@Valid @RequestPart(required = false) CreateProduct.Request request,
            @RequestPart(required = false) List<MultipartFile> images*/) {
        log.info("[ProductController] createProduct called");
        Long memberId = 1L;

        CreateProduct.Response response = productService.createProduct(request, memberId);

        /*Optional.ofNullable(images)
                .ifPresent(i -> imageService.storeImageProducts(i, response.getProductId()));*/

        URI uri = URI.create(String.format("/api/products/%s", response.getProductId()));
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{product-id}")
    public ResponseEntity<UpdateProduct.Response> updateProduct(@PathVariable("product-id") String productId,
                                                                @Valid @RequestBody UpdateProduct.Request request) {
        log.info("[ProductController] updateProduct called");
        Long memberId = 1L;

        return ResponseEntity.ok(productService.updateProduct(request, productId, memberId));
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("product-id") String productId) {
        log.info("[ProductController] deleteProduct called");
        Long memberId = 1L;
        productService.deleteProduct(productId, memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductDetailDto> findProductDetail(@PathVariable("product-id") String productId,
                                                              HttpServletRequest request,
                                                              HttpServletResponse response) {
        log.info("[ProductController] findProductDetail called");
        Long memberId = 1L;

        // 조회수 로직
        countView(productId, request, response);

        return ResponseEntity.ok(productService.findProductDetail(productId));
    }

    @GetMapping("/members")
    public ResponseEntity<List<ProductDto>> findProducts(@RequestParam int size, @RequestParam int page) {
        log.info("[ProductController] findProducts called");
        Long memberId = 2L;

        return ResponseEntity.ok(productService.findProducts(memberId, size, page));
    }

    @GetMapping("/featured")
    public ResponseEntity<FeaturedProductsResponseDto> findFeaturedProducts() {

        List<Product> top3ByViewCount = productService.getTop3ByViewCount();
        List<Product> top3ByTotalRateScoreRatio = productService.getTop3ByTotalRateScoreRatio();
        List<Product> top3ByBaseFeeZero = productService.getTop3ByBaseFeeZero(0);

        FeaturedProductsResponseDto responseDTO =
                ProductMapper.createFeaturedProductsResponseDto(top3ByViewCount, top3ByTotalRateScoreRatio, top3ByBaseFeeZero);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    ResponseEntity<PageResponseDto> searchProducts(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        Page<Product> searchResults = productService.searchProduct(keyword, page, size);
        PageResponseDto response = ProductMapper.createPageResponseDto(searchResults);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void countView(String productId, HttpServletRequest request, HttpServletResponse response) {
        /* 조회수 로직 */
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("["+ productId +"]")) {
                productService.updateView(productId);
                oldCookie.setValue(oldCookie.getValue() + "_[" + productId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24); 							// 쿠키 시간
                response.addCookie(oldCookie);
            }
        } else {
            productService.updateView(productId);
            Cookie newCookie = new Cookie("postView", "[" + productId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24); 								// 쿠키 시간
            response.addCookie(newCookie);
        }
    }
}
