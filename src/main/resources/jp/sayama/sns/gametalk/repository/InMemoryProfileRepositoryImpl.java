package jp.sayama.sns.gametalk.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jp.sayama.sns.gametalk.model.Profile;

@Repository
public class InMemoryProfileRepositoryImpl implements ProfileRepository{

	private final List<Profile> profiles = new ArrayList<>(List.of(
			new Profile(
				1L,
	            "ユーザ名",
	            "valorant, LOLL, Apex Legend",
	            "フレンド募集中！",
	            "主に夜22時以降にプレイしています。ランクマッチ中心。VC可。"
	        )
	));

	// 次のIDを表すプロパティ
	private long nextId = 2;

	@Override
    public List<Profile> findAll() {
        return new ArrayList<>(profiles);
    }

    @Override
    public void register(Profile profile) {
        Profile newProfile = new Profile(
            nextId++,
            profile.getName(),
            profile.getGames(),
            profile.getComment(),
            profile.getExtra(),
            profile.getIconPath()
        );
        profiles.add(newProfile);
    }
    @Override
    public void update(Profile profile) {
        for (Profile p : profiles) {
            	if (p.getId().equals(profile.getId())) {
                    p.setName(profile.getName());
                    p.setGames(profile.getGames());
                    p.setComment(profile.getComment());
                    p.setExtra(profile.getExtra());
                    p.setIconPath(profile.getIconPath()); 
     
                    return;
            }
        }
    }
    @Override
	public Optional<Profile> findById(Long id) {
	    return profiles.stream()
	                   .filter(p -> p.getId().equals(id))
	                   .findFirst();
	}
    @Override
    public Optional<Profile> findByUserName(String userName) {
        return profiles.stream()
                       .filter(p -> userName.equals(p.getUserName()))
                       .findFirst();
    }

    @Override
    public void delete(Profile profile) {
        profiles.removeIf(p -> p.getId().equals(profile.getId()));
    }
    @Override
    public List<Profile> findByNameContaining(String keyword) {
        List<Profile> matched = new ArrayList<>();
        for (Profile p : profiles) {
            if (p.getName() != null && p.getName().contains(keyword)) {
                matched.add(p);
            }
        }
        return matched;
    }

}
