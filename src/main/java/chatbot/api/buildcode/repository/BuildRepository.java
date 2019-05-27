package chatbot.api.buildcode.repository;

import chatbot.api.buildcode.domain.Build;

public interface BuildRepository {

    void save(Build build);

    void update(Build reBuild);

    void delete(String providerId);

    Build find(String providerId);
}
