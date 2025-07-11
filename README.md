# SEG

## 概要

Segは、テキストコミュニケーションに特化したSNSアプリです。<br>
従来のSNSアプリは、画像や動画などのメディア投稿が主流となっていますが、<br>
Segではあえてこれらのメディア機能を排除し、言葉による表現に焦点を当てています。<br>
プログラミングを学ぶ中で、言語表現の価値を再認識した経験から、<br>
ユーザーが言葉を通じて自身の考えやアイデアを世界に発信できるプラットフォームを提供したいと考え、Segを開発しました。<br>

## 技術スタック

クライアント: Kotlin, Jetpack Compose<br>
サーバーサイド: Supabase<br>

使用アーキテクチャ: MVVM(MVIに移行中), Clean Architecture(？)<br>
使用ツール: Ktlint<br>

## 工夫した点

プロジェクトの拡張性を重視し、特定のアーキテクチャと設計パターンを採用しました。<br>
それに加えて、DIを活用しモジュール間の結合度を下げ、メンテナンスを容易にしました。<br>

## 各モジュールの依存関係

![seg-dependency](https://github.com/user-attachments/assets/e632063c-c68c-432c-b423-cce40cb8768a)

(※ 書き方間違っている可能性があります)


## アプリを使用する際の注意点

Supabaseを使用するのにkeyを使用するので、直接ビルドしても使用できません。
なので、実際に動くアプリを見たい場合は、APKファイルを渡すので、X等でお伝え下さい。
(その際に生じた損失等に関しては、こちらは一切責任を負いません。ご了承ください。)
(今年度中までには、このアプリをGoogle Playに上げたいと思っているので、期待せずに待っててくださると助かります。)

## 画像
![Screenshot_20250624-123347](https://github.com/user-attachments/assets/83afea2f-0144-40bd-873a-f68b6b68b68d)
![Screenshot_20250624-123351](https://github.com/user-attachments/assets/04a893b3-a5e6-4197-9e59-cbe722ddc40e)
![Screenshot_20250624-123354](https://github.com/user-attachments/assets/24259c1d-5cf4-4d2c-b740-d972da4845eb)
![Screenshot_20250624-123405](https://github.com/user-attachments/assets/ee7edcb4-f890-4827-88cc-5d7b199dbac0)
![Screenshot_20250624-123426](https://github.com/user-attachments/assets/2d67ef67-8b49-49f6-a09e-c8ca4df06fbf)
![Screenshot_20250624-123430](https://github.com/user-attachments/assets/330a27d2-eb35-44e4-8759-c54dbaa473f1)
![Screenshot_20250624-123450](https://github.com/user-attachments/assets/90534b99-c9b0-4db8-947d-526bc1e867d4)


