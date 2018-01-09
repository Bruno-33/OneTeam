package com.example.administrator.oneteam.Service;

/**
 * Created by Administrator on 2017/12/30 0030.
 */

import com.example.administrator.oneteam.model.Expenditure;
import com.example.administrator.oneteam.model.Outcome;
import com.example.administrator.oneteam.model.Person;
import com.example.administrator.oneteam.model.Task;
import com.example.administrator.oneteam.model.TaskDetail;
import com.example.administrator.oneteam.model.time_sheet;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public abstract interface BrunoService {
    @GET("/users")//获取limit个用户 从数据库的第offfset个开始获取
    public abstract Observable<List<Person>> getUsers(@Query("limit") int limit,@Query("offset") int offset);

    @GET("/signin")//登录 提供密码和用户名 成功登录返回“TRUE”
    public abstract Observable<Outcome> signin(@Query("name") String name,@Query("password") String password);

    @GET("/signup")//注册 提供用户名和密码  团队id  成功返回“TRUE” 重名返回“NAMEERROR” 团队id不存在返回“NOTHISTEAM”
    public abstract Observable<Outcome> signup(@Query("name") String name,@Query("password") String password,@Query("team_id") String team_id);

    @GET("/tasks")//获取用户 参数同getUsers接口
    public abstract Observable<List<Task>> getTasks(@Query("limit") int limit,@Query("offset") int offset);

    @GET("/tasks_done")//获取用户 参数同getUsers接口
    public abstract Observable<List<Task>> getTasks_done(@Query("limit") int limit,@Query("offset") int offset);

    @GET("/tasks_undone")//获取用户 参数同getUsers接口
    public abstract Observable<List<Task>> getTasks_undone(@Query("limit") int limit,@Query("offset") int offset);

    @GET("/task_detail")//参数 task_id 和 person_id  返回某个任务被某个人领取的详情
    public abstract Observable<List<TaskDetail>> getTaskDetail(@Query("task_id") String task_id);

    @GET("/time_sheet")//参数 日期 和 person_id 返回当天考勤情况
    public abstract Observable<List<time_sheet>> getTime_sheet(@Query("person_id") String person_id,@Query("date") String date);

    @GET("/set_time_sheet")//参数person_id 开始时间和总时间  成功返回“TRUE”
    public abstract Observable<Outcome> set_time_sheet(@Query("person_id") String person_id,@Query("start_time") String start_time,@Query("total_time") String total_time);

    @GET("/new_task")
    public abstract Observable<Outcome> new_task(@Query("person_id") String person_id,@Query("name") String name,@Query("max") String max
    ,@Query("ddl") String ddl,@Query("budget") String budget,@Query("mask") String mask ,@Query("description") String description
    );
    @GET("/done_task")
    public abstract Observable<Outcome> done_task(@Query("task_id") String task_id);

    @GET("/undone_task")
    public abstract Observable<Outcome> undone_task(@Query("task_id") String task_id);
    @GET("/new_expense")
    public abstract Observable<Outcome> new_expense(@Query("person_id") String person_id,@Query("task_id") String task_id,@Query("num") String num
            ,@Query("time") String time ,@Query("description") String description
    );
    @GET("/get_task")
    public abstract Observable<Task> get_task(@Query("task_id") String task_id);
    @GET("/update_user")
    public abstract Observable<Outcome> update_user(@Query("person_id") String person_id,@Query("name") String name,@Query("sex") String sex
            ,@Query("age") String age ,@Query("email") String email
    );
    @GET("/get_expense")
    public abstract Observable<List<Expenditure>> get_expense(@Query("task_id") String task_id,@Query("person_id") String person_id);
    @GET("/user")
    public abstract Observable<Person> getUser(@Query("id") String id);

    @Multipart//上传图片 成功返回“TRUE”
    @POST("upload")
    public abstract Observable<Outcome>  upload(@Part() MultipartBody.Part file);

}
