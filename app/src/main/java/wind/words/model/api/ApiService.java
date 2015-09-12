package wind.words.model.api;

/**
 * Created by wind on 2015/9/12.
 */
public class ApiService {

    public static Api getApi(){
        return new ApiImpl();
    }
}
