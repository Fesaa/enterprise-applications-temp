package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.dtos.tags.CreateTagDto;
import art.ameliah.ehb.anki.api.exceptions.UnAuthorized;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.tags.Tag;
import art.ameliah.ehb.anki.api.services.model.ITagService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final ITagService tagService;

    @PostMapping
    public Tag createTag(@RequestBody CreateTagDto tag) {
        return tagService.createTag(User.current(), tag.getName(), tag.getHexColour());
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

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id, @Nullable @RequestParam Boolean force) {
        Tag tag = tagService.getTag(id).orElseThrow();

        force = force != null && force && User.current().isAdmin();
        if (!tag.getUser().getId().equals(User.current().getId()) && !force) {
            throw new UnAuthorized();
        }

        tagService.deleteTag(id, force);
    }

}
