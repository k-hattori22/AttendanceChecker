package jp.kobe_u.cs.daikibo.attendancechecker.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobe_u.cs.daikibo.attendancechecker.entity.Attendance;
import jp.kobe_u.cs.daikibo.attendancechecker.repository.AttendanceRepository;
import jp.kobe_u.cs.daikibo.attendancechecker.repository.UserRepository;

@Service

public class AttendanceService {
  
    @Autowired
    AttendanceRepository attrepo; //出席管理
    UserRepository userrepo; //学生台帳

  
    // UC:出退勤を登録する
    /*  1.学生ID入力される
        2.そいつが学生台帳に存在するか確認（学生台帳を検索）
        3.存在したら、現時点（入力前）でのboolean「on/off」の反転した出退勤情報を作成し、レポジトリに追加*/
    public Attendance postAttendance (Attendance att) {

        //1
        String userId = att.getUserId();
        //2
        boolean exist = userrepo.existsById(userId);
        if(exist){
            //3
            att.setCreatedAt(new Date());  //作成日時をセット
            return attrepo.save(att); //セーブしたオブジェクトを返却
        }
    }
  
    // 出席情報を取得する
    /*全つぶやきの取得がベース
      AttendanceRepositoryを全て見る findAll()
      情報が新しいほうか古いほうか知らんけど、新しい方からと仮定
      すべてのユーザーの最新の出席情報を取得listに格納しreturn
      （探索の際、既にlistに格納済みのユーザーは、ユーザーIDをhashsetに格納して記録。重複しない。）
      もっといい方法あったらオナシャス
     */
    public List<Attendance> getAttendance() {
        Set<String> set = new HashSet<>();

        Iterable<Attendance> found = attrepo.findAll();
        ArrayList<Attendance> list = new ArrayList<>();
        
        //found.forEach(list::add);

        for (Attendance attendance : found) {  //拡張for文
            if(!set.contains(attendance.getUserId())){ //出席情報(attendance)のユーザーIDがsetになければ↓2行
                set.add(attendance.getUserId());//attendanceをsetに格納
                list.add(attendance);//attendanceをlistに格納
            }
        }
        return list;
    }
 }

