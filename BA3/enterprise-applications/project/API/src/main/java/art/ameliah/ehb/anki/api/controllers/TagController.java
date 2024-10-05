package art.ameliah.ehb.anki.api.controllers;

import art.ameliah.ehb.anki.api.annotations.Admin;
import art.ameliah.ehb.anki.api.annotations.BaseController;
import art.ameliah.ehb.anki.api.dtos.tags.CreateTagDto;
import art.ameliah.ehb.anki.api.dtos.tags.TagDto;
import art.ameliah.ehb.anki.api.exceptions.UnAuthorized;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.tags.Tag;
import art.ameliah.ehb.anki.api.services.model.ITagService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@BaseController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final ITagService tagService;
    private final ModelMapper modelMapper;

    @PostMapping
    public TagDto createTag(@RequestBody CreateTagDto tag) {
        Tag t = tagService.createTag(User.current(), tag.getName(), tag.getHexColour());
        return modelMapper.map(t, TagDto.class);
    }

    @GetMapping
    public List<TagDto> getAllTagsForUser() {
        return tagService.getTags(User.current())
                .stream()
                .map(t -> modelMapper.map(t, TagDto.class))
                .toList();
    }

    @Admin
    @GetMapping("/all")
    public List<TagDto> getAllTags() {
        return tagService.getTags()
                .stream()
                .map(t -> modelMapper.map(t, TagDto.class))
                .toList();
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable Long id) {
        if (id == null) {
            throw new NoSuchElementException();
        }

        return modelMapper.map(tagService.getTag(id).orElseThrow(), TagDto.class);
    }

    @GetMapping("/fuzzy/{prefix}")
    public List<TagDto> getFuzzyTags(@PathVariable String prefix) {
        if (prefix == null) {
            throw new NoSuchElementException();
        }

        return tagService.getTags(prefix)
                .stream()
                .map(t -> modelMapper.map(t, TagDto.class))
                .toList();
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
