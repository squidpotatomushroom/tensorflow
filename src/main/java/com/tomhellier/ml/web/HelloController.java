package com.tomhellier.ml.web;

import com.google.gson.Gson;
import com.tomhellier.ml.tensorflow.ImageClassifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.tensorflow.Graph;
import org.tensorflow.TensorFlow;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.tomhellier.ml.tensorflow.ImageClassifier.readAllLinesOrExit;

@Controller
@Component
public class HelloController {

    private ImageClassifier ic = new ImageClassifier();

    @RequestMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/tensor")
    public String tensor() {
        final String value = "Hello from " + TensorFlow.version();
        return value;
    }

    @RequestMapping("/possible")
    public String possible() {

        String modelDir = "src/main/resources";

        List<String> labels =
                readAllLinesOrExit(Paths.get(modelDir, "imagenet_comp_graph_label_strings.txt"));
        return new Gson().toJson(labels);
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        return "uploadForm";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        // Classify the image, and add as an attribute to thymeleaf
        redirectAttributes.addFlashAttribute("classification", "We estimate this is a: " + ic.classifyImage(file));

        return "redirect:/";
    }

}