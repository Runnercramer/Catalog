package com.cris.mvc.catalogproducts.controllers;

import com.cris.mvc.catalogproducts.dtos.CategoryDTO;
import com.cris.mvc.catalogproducts.dtos.ProductDTO;
import com.cris.mvc.catalogproducts.models.Category;
import com.cris.mvc.catalogproducts.services.ICategoryService;
import com.cris.mvc.catalogproducts.services.IImageService;
import com.cris.mvc.catalogproducts.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private IProductService productService;
    private IImageService imageService;
    private ICategoryService categoryService;

    @Autowired
    public ProductController(IProductService productService, IImageService imageService, ICategoryService categoryService) {
        this.productService = productService;
        this.imageService = imageService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products/categories/{name}")
    public String listProductsByCategory(@PathVariable String name, Model model) {
        List<ProductDTO> products = productService.findByCategoryName(name);
        model.addAttribute("category", name);
        model.addAttribute("products", products);
        return "products-by-category";
    }

    @GetMapping("/products/create")
    public String createProductsView(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", categoryService.findAll());
        return "new-product";
    }

    @PostMapping("/products/create")
    public String createProduct(@ModelAttribute ProductDTO product,
                                @RequestParam("imageFile") MultipartFile file,
                                @RequestParam(value = "categoryName", required = false) String categoryName) {
        product.setImage(this.imageService.saveImage(file));

        CategoryDTO category = categoryService.findByName(categoryName);
        product.setCategoryDTO(category);

        productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products/update/{id}")
    public String updateProductView(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("id", id);
        return "edit-product";
    }

    @PostMapping("/products/update")
    @Transactional
    public String updateProduct(@ModelAttribute ProductDTO product,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                @RequestParam Long categoryId){
        product.setImage(this.imageService.saveImage(imageFile));
        this.productService.updateProduct(product, categoryId);
        return "redirect:/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
