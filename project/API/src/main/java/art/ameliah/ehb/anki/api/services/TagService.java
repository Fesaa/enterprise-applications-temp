package art.ameliah.ehb.anki.api.services;

import art.ameliah.ehb.anki.api.dtos.tags.TagDto;
import art.ameliah.ehb.anki.api.exceptions.AppException;
import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.tags.Tag;
import art.ameliah.ehb.anki.api.models.tags.query.QTag;
import art.ameliah.ehb.anki.api.services.model.IStringService;
import art.ameliah.ehb.anki.api.services.model.ITagService;
import io.ebean.DB;
import io.ebeaninternal.server.util.Str;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagService implements ITagService {

    private static String DEFAULT_HEX = "#fff";

    private final IStringService stringService;

    @Override
    public Optional<Tag> getTag(Long id) {
        return new QTag()
                .id.eq(id)
                .findOneOrEmpty();
    }

    @Override
    public Optional<Tag> getTag(String name) {
        return new QTag()
                .normalizedName.eq(stringService.normalize(name))
                .findOneOrEmpty();
    }

    @Override
    public boolean inUse(Long id) {
        return !new QTag()
                .id.eq(id)
                .decks.fetch()
                .findOneOrEmpty()
                .orElseThrow()
                .getDecks()
                .isEmpty();

    }

    @Override
    public List<Tag> getTags() {
        return new QTag().findList();
    }

    @Override
    public List<Tag> getTags(User user) {
        return new QTag()
                .user.id.eq(user.getId())
                .findList();
    }

    @Override
    public List<Tag> getTags(String prefix) {
        return new QTag()
                .normalizedName.startsWith(stringService.normalize(prefix))
                .findList();
    }

    @Override
    public Tag createTag(User user, String name) {
        return createTag(user, name, null);
    }

    @Override
    public Tag createTag(User user, String name, String hexColour) {
        Optional<Tag> optionalTag = getTag(name);
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        }

        Tag tag = Tag.builder()
                .user(user)
                .name(name)
                .normalizedName(stringService.normalize(name))
                .hexColour(this.orDefaultHex(hexColour))
                .build();
        tag.save();
        return tag;
    }

    @Override
    public Tag updateTag(TagDto dto) {
        Tag tag = DB.reference(Tag.class, dto.getId());
        tag.setName(dto.getName());
        tag.setNormalizedName(dto.getNormalizedName());
        tag.setHexColour(this.orDefaultHex(dto.getHexColour()));

        tag.save();
        return tag;
    }

    private String orDefaultHex(String hex) {
        if (hex == null || hex.isEmpty()) {
            return DEFAULT_HEX;
        }
        return hex;
    }

    @Override
    public void deleteTag(Long id) {
        deleteTag(id, false);
    }

    @Override
    public void deleteTag(Long id, boolean force) {
        if (inUse(id) && !force) {
            throw new AppException("tag.errors.in-use");
        }

        getTag(id).ifPresent(Tag::delete);
    }
}
