## 実装済のユースケース
### ログイン・ログアウト
- 権限は学生，管理者，カードリーダーの三つ
- ログインに成功すると，学生と管理者は座席一覧画面へ，
カードリーダーは出退勤読み取り画面へ遷移する．
- 管理者アカウント
    - ユーザ： admin   パスワード: 0000
- カードリーダーアカウント
    - ユーザ： card   パスワード: reader
- アカウント設定は，applications.propertiesを参照

### 出退勤登録
- カードリーダーアカウントで実行可能
- ユーザーIDを入力することで，そのユーザーの出退勤情報を登録する

## 実行方法
1. MySQLに新規データベースattendanceを作成
    - ユーザ： attendance　パスワード：checker に全権委譲 (grant all) すること
1. VS-CodeでAttendanceApplication.java をF5で実行
1. http://localhost:2289/ を開く
1. 管理者アカウントでログイン
1. ユーザを適宜登録する（未実装）
1. カードリーダーアカウントでログインし，登録ユーザーを読み取る
1. 学生アカウントで座席一覧を確認

## 変更箇所（要相談）
### ディレクトリ階層構造
- ログイン機能の実装の際に，会議予約アプリ（https://github.com/muretti0114/tokuronI2020/tree/master/MeetingRoomReservation）を参考にしたため
- 元のコードと混ざるとややこしいので，新しくフォルダを作ってそこに入れた
- 見づらければ元の階層構造に直すこともできるかも？

### Userクラスに出欠状況を表す列挙型を追加
- public enum AttendanceStatus{PRESENT, ABSENT}
- 過去の出欠状況をいちいち走査する必要がなくなる

### AttendanceクラスのisAttendanceをAttendanceStatusに変更
- この方が直感的に分かりやすい気がしたため
