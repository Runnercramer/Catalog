package com.cris.mvc.catalogproducts.controllers;

import com.cris.mvc.catalogproducts.dtos.CategoryDTO;
import com.cris.mvc.catalogproducts.services.ICategoryService;
import com.cris.mvc.catalogproducts.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class CategoryController {

    private ICategoryService categoryService;
    private IImageService imageService;

    @Autowired
    public CategoryController(ICategoryService categoryService, IImageService imageService) {
        this.categoryService = categoryService;
        this.imageService = imageService;
    }

    @GetMapping("/index")
    public String listCategories(Model model) {
        List<CategoryDTO> categoryDTOS = categoryService.findAll();
        model.addAttribute("categories", categoryDTOS);
        return "categories";
    }

    @GetMapping("/categories/create")
    public String createCategoryView(Model model){
        model.addAttribute("category", new CategoryDTO());
        return "new-category";
    }

    @PostMapping("/categories/create")
    public String createCategory(@ModelAttribute CategoryDTO category,
                                 @RequestParam("imageFile") MultipartFile file){
        category.setImage(this.imageService.saveImage(file));
        categoryService.createCategory(category);
        return "redirect:/index";
    }

    @GetMapping("/categories/edit/{name}")
    public String editCategoryView(@PathVariable String name,
                               Model model){
        CategoryDTO category = categoryService.findByName(name);
        model.addAttribute("category", category);
        return "edit-category";
    }

    @PostMapping("/categories/edit")
    public String editCategory(@ModelAttribute CategoryDTO category,
                               @RequestParam("imageFile") MultipartFile imageFile){
        category.setImage(this.imageService.saveImage(imageFile));
        categoryService.updateCategory(category);
        return "redirect:/index";
    }

}
