package Controllers;

import Components.Model;
import Components.Response;
import Models.*;

import java.util.List;
import java.util.Map;

public class UserController extends BaseController{
    @Override
    public Map<String, List<String>> accessControl() {
        return Map.of(
                "actionAddStudent", List.of("admin"),
                "actionAddStaff", List.of("admin")
        );
    }

    public Response actionAddStudent(List<String> args){
        Student student = new Student(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6));
        boolean isSaved = student.save();
        if(!isSaved){
            if(student.getErrors().containsValue(Student.UNIQUE_ERR)){
                //Category.UNIQUE_ERR
                return new Response(1); //dup-id
            }
            //Category.PARENT_NULL_ERR
            return new Response(2); //not-found
        }
        return new Response(0); //success
    }

    public Response actionAddStaff(List<String> args){
        Staff staff;
        if(args.get(7).equals("staff")){
            staff = new Staff(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6));
        }else{
            staff = new Professor(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6));
        }
        boolean isSaved = staff.save();
        if(!isSaved){
            if(staff.getErrors().containsValue(Student.UNIQUE_ERR)){
                //Category.UNIQUE_ERR
                return new Response(1); //dup-id
            }
            //Category.PARENT_NULL_ERR
            return new Response(2); //not-found
        }
        return new Response(0); //success
    }
}
