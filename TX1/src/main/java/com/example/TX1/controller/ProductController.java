package com.example.TX1.controller;
import com.example.TX1.entity.Product;
import com.example.TX1.entity.Category;
import com.example.TX1.form.ProductForm;
import com.example.TX1.repository.ProductRepository;
import com.example.TX1.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository products;
    private final CategoryRepository categories;

    public ProductController(ProductRepository products,CategoryRepository categories){
        this.products=products;
        this.categories=categories;
    }

    @ModelAttribute("categories")
    public java.util.List<Category> categoryList(){
        return categories.findAll(Sort.by("name"));
    }

    @GetMapping
    public String list(
            @RequestParam(defaultValue="") String keyword,
            @RequestParam(required=false) Long categoryId,
            @RequestParam(defaultValue="0") int page,
            Model model
    ){
        Page<Product> results = products.search(keyword.isBlank()?null:keyword.trim(),categoryId,PageRequest.of(Math.max(page,0),10,Sort.by(Sort.Direction.DESC,"id")));
        model.addAttribute("products",results);
        model.addAttribute("keyword",keyword);
        model.addAttribute("categoryId",categoryId);
        return "products/list";
    }

    @GetMapping("/new")
    public String create(Model model){
        model.addAttribute("productForm",new ProductForm());
        return "products/form";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id,Model model){
        Product p=getProduct(id);
        ProductForm form=new ProductForm();
        form.setId(p.getId());
        form.setName(p.getName());
        form.setPrice(p.getPrice());
        form.setStock(p.getStock());
        form.setCategoryId(p.getCategory().getId());
        model.addAttribute("productForm",form);
        return "products/form";
    }

    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("productForm") ProductForm form,
            BindingResult errors
    ){
        Category category=null;
        if(form.getCategoryId()!=null){
            category=categories.findById(form.getCategoryId()).orElse(null);
            if(category==null)
                errors.rejectValue("categoryId","invalid","Danh mục không tồn tại");
        }
        if(errors.hasErrors())return "products/form";
        Product p=form.getId()==null?new Product():getProduct(form.getId());
        p.setName(form.getName());
        p.setPrice(form.getPrice());
        p.setStock(form.getStock());
        p.setCategory(category);
        products.save(p);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        Product p=getProduct(id);products.delete(p);
        return "redirect:/products";
    }

    private Product getProduct(Long id){
        return products.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy sản phẩm"));
    }
}