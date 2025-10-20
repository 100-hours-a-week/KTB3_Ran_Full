package com.ran.community.user.dto.response;

public class UserDataResponseDTO {
    private long userId;
    private String username;
    private String email;

    public UserDataResponseDTO(long userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    //get을 이용할 일이 없다고 생각하고 get을 뺏음.
    //그럼 response에는 서버에서 클라이언트에게 주기 위해 서버가 넣어서 보내주는 용도라서 set만 있고,
    //request에는 클라이언트에서 서버에게 주기 때문에 서버가 그 안에 있는 값을 가져와야되므로 get만 사용하나?

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
