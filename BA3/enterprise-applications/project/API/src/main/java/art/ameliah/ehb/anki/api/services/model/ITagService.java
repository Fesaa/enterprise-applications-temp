package art.ameliah.ehb.anki.api.services.model;

import art.ameliah.ehb.anki.api.models.tags.Tag;

import java.util.List;
import java.util.Optional;

public interface ITagService {

    Optional<Tag> getTag(Long id);
    Optional<Tag> getTag(String name);

    List<Tag> getTags();
    List<Tag> getTags(String prefix);

    Tag createTag(String name);
    Tag createTag(String name, String hexColour);

}
