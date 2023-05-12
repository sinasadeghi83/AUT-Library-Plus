package Rbac;

import Components.Auth;
import Components.Model;
import Components.Rule;
import Models.Borrow;
import Models.Fine;
import Models.Staff;
import Models.User;

import java.util.List;
import java.util.Map;

public class BorrowRule extends Rule {
    @Override
    public boolean execute(Auth auth, String item, Map<String, Object> params) {
        Borrow borrow = (Borrow) params.get("borrowObj");
        User user = (User) auth;
        if(user.getFine() != null)
            return false;

        List<Model> thisUserBorrows = Model.find(Borrow.class, Map.of(
                "userId", auth.getId(),
                "resId", borrow.getResId()
        ));

        if(thisUserBorrows.size() > 0)
            return false;

        List<Model> userBorrows = Model.find(Borrow.class, Map.of(
                "userId", auth.getId()
        ));

        if(user instanceof Staff){
            return userBorrows.size() < 5;
        }else {
            return userBorrows.size() < 3;
        }
    }
}
