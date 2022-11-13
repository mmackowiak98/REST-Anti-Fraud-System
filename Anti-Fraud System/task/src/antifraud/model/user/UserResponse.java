package antifraud.model.user;


import lombok.Getter;

@Getter
public class UserResponse {
    private final String name;
    private final String username;

    public UserResponse(User user) {
        this.name = user.getName();
        this.username = user.getUsername();
    }


}
