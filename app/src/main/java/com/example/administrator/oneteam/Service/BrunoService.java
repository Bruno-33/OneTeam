package com.example.administrator.oneteam.Service;

/**
 * Created by Administrator on 2017/12/30 0030.
 */

import com.example.administrator.oneteam.model.Outcome;
import com.example.administrator.oneteam.model.Person;
import com.example.administrator.oneteam.model.Task;
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

    @GET("/task")//参数 task_id 和 person_id  返回某个任务被某个人领取的详情
    public abstract Observable<Task> getTaskDetail(@Query("task_id") String task_id,@Query("person_id") String person_id);

    @GET("/time_sheet")//参数 日期 和 person_id 返回当天考勤情况
    public abstract Observable<List<time_sheet>> getTime_sheet(@Query("person_id") String person_id,@Query("date") String date);

    @GET("/set_time_sheet")//参数person_id 开始时间和总时间  成功返回“TRUE”
    public abstract Observable<Outcome> set_time_sheet(@Query("person_id") String person_id,@Query("start_time") String start_time,@Query("total_time") String total_time);

    @Multipart//上传图片 成功返回“TRUE”
    @POST("upload")
    public abstract Observable<Outcome>  upload(@Part() MultipartBody.Part file);

}