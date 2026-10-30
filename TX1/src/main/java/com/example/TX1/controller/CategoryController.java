package com.example.TX1.controller;
import com.example.TX1.entity.Category;
import com.example.TX1.repository.CategoryRepository;
import com.example.TX1.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categories;
    private final ProductRepository products;
    public CategoryController(CategoryRepository categories,ProductRepository products){
        this.categories=categories;
        this.products=products;
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("categories",categories.findAll());
        return "categories/list";
    }

    @GetMapping("/new")
    public String create(Model model){
        model.addAttribute("category",new Category());
        return "categories/form";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id,Model model){
        model.addAttribute("category",getCategory(id));
        return "categories/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("category") Category form,BindingResult errors){
        if(errors.hasErrors())
            return "categories/form";
        Category category=form.getId()==null?new Category(): getCategory(form.getId());
        category.setName(form.getName());category.setDescription(form.getDescription());
        categories.save(category);
        return "redirect:/categories";
    }

    @PostMapping("/{id}/delete") public String delete(
            @PathVariable Long id,
            RedirectAttributes redirect
    ){
        if(!categories.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        if(products.existsByCategoryId(id)){
            redirect.addFlashAttribute("error","Danh mục đang có sản phẩm, không thể xóa.");
        }
        else{
            categories.deleteById(id);
            redirect.addFlashAttribute("success","Đã xóa danh mục.");
        }

        return "redirect:/categories";
    }

    private Category getCategory(Long id){
        return categories.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Không tìm thấy danh mục"
                        )
                );
    }
}