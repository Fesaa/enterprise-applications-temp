package art.ameliah.ehb.anki.api.services.model;

import art.ameliah.ehb.anki.api.models.account.User;
import art.ameliah.ehb.anki.api.models.tags.Tag;

import java.util.List;
import java.util.Optional;

public interface ITagService {

    Optional<Tag> getTag(Long id);
    Optional<Tag> getTag(String name);

    boolean inUse(Long id);

    List<Tag> getTags();
    List<Tag> getTags(String prefix);

    Tag createTag(User user, String name);
    Tag createTag(User user, String name, String hexColour);

    void deleteTag(Long id);
    void deleteTag(Long id, boolean force);

}
