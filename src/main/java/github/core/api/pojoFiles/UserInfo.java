package github.core.api.pojoFiles;

public class UserInfo {
    public String login;
    public int id;
    public String node_id;
    public String avatar_url;
    public Plan plan;


    public class Plan {
        public String name;
        public long space;
        public int collaborators;
        public int private_repos;
    }
}


