package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.dtos.tags.CreateTagDto;
import art.ameliah.ehb.anki.api.models.tags.Tag;
import art.ameliah.ehb.anki.api.services.model.ITagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Log
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final ITagService tagService;

    @PostMapping
    public Tag createTag(@RequestBody CreateTagDto tag) {
        return tagService.createTag(tag.getName(), tag.getHexColour());
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getTags();
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable Long id) {
        if (id == null) {
            throw new NoSuchElementException();
        }

        return tagService.getTag(id).orElseThrow();
    }

    @GetMapping("/fuzzy/{prefix}")
    public List<Tag> getFuzzyTags(@PathVariable String prefix) {
        if (prefix == null) {
            throw new NoSuchElementException();
        }

        return tagService.getTags(prefix);
    }



}
