import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Accounts {
    private List<User> _accounts = new ArrayList<User>();

    public boolean contains_nickname(String val){
        return _accounts.stream().map(User::get_nickname).anyMatch(val::equals);
    }

    public boolean check_password(String nickname, String password){
        return _accounts.stream().map(User::get_login_credentials).filter(entry -> entry.containsKey(nickname)).map(entry -> entry.get(nickname)).anyMatch(password::equals);
    }

    public int get_security_questionID(String nickname){
        return _accounts.stream().filter(entry -> entry.get_nickname().equals(nickname)).findFirst().map(User::get_security_question_id).get();
    }

    public boolean check_security_qa(int question_id, String answer){
        return _accounts.stream().map(User::get_security_qa_map).filter(entry -> entry.containsKey(question_id)).map(entry -> entry.get(question_id)).anyMatch(answer::equals);
    }

    public void addUser(User user){
        _accounts.add(user);
    }

    public User get_User(String nickname){
        return _accounts.stream().filter(entry -> entry.get_nickname().equals(nickname)).findFirst().get();
    }

}
