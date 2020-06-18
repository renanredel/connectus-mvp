package renan.augusto.connectusprototype;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIServer {

    @GET("Api/UserRetrive")
    Call<List<User>> getAllUser();

    @GET("user/find/{usernumber}")
    Call<List<User>> getSpecificUser(@Path("usernumber") String RegistrationNumber);

    @POST("Api/Userregistration")
    Call<User>createUser(@Body User user);

    @PUT("Api/Userupdate/{id}")
    Call<User>updateUser(@Path("id") String RegistrationNumber, @Body User user);

    @DELETE("user/remove/{id}")
    Call<User>deleteUser(@Path("id") String RegistrationNumber);
}
