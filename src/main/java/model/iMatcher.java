package model;

import java.util.List;

public interface iMatcher {
    Pet findBestMatch(UserProfile user, List<Pet> pets);
}
