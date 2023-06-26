# ユースケース 1：出勤情報を登録する

## 概要
学生が出勤情報を登録をする

## アクター
- 学生

## 事前条件
- 学生がシステムにログインしていること．まだ出勤していないこと．

## 事後条件
- 出勤情報が登録される

## トリガ―
- 学生が研究室の機械にカードをかざす

## 基本フロー
1. 学生は，研究室の機械にカードをかざす
2. システムは，出勤条件をチェックし，問題がなければ，学生が出勤したことを登録する
3. システムは，出勤登録した学生情報を研究室マップに表示する

## 代替フロー
### 代替フロー1
- 2a.1 基本フロー2で出席条件（登録されている学生情報にない，既に出勤情報がある）に引っかかる場合，システムは出勤エラーを出して，1に戻る．

## GUI紙芝居
### ログイン画面