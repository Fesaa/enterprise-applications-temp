package art.ameliah.ehb.anki.api.models;

import art.ameliah.ehb.anki.api.models.account.User;

public interface Ownable {

    User owner();

    default boolean isOwner(Long userId) {
        return owner().getId().equals(userId);
    }

    default boolean isOwner(User user) {
        return isOwner(user.getId());
    }

}
