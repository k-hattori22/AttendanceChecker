package jp.kobe_u.cs.daikibo.attendancechecker.service;

import java.util.ArrayList;

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
          おらへんかったら、戻る？
        3.存在したら、現時点（入力前）でのboolean「on/off」の反転した出退勤情報を作成し、レポジトリに追加
        4.*/
    public Attendance postAttendance (Attendance att) {

        //1
        String userId = att.getUserId();
        //2
        Iterable<Attendance> found = attrepo.findByCommentContaining(userId);
        if(){

        }
        //3
        att.setCreatedAt(new Date());  //作成日時をセット
        
        return attrepo.save(att); //セーブしたオブジェクトを返却
    
    }

        att.setCreatedAt(new Date());  //作成日時をセット
        return attrepo.save(att); //セーブしたオブジェクトを返却
    }

  
    // 出席情報を取得する
    /*全つぶやきの取得がベース
      1. 本日の0時以降から、最終更新isAttendanceが1になっている学生を検索
      2. その学生をlist or Setに入れてreturn。（リストにない学生は全員isAttendance==0の扱いで！）
     */
    public List<Tsubuyaki> getAllTsubuyaki() {

        Iterable<Tsubuyaki> found = attrepo.findAll();

        ArrayList<Tsubuyaki> list = new ArrayList<>();
        
        found.forEach(list::add);
  
        return list;
    }
 }

