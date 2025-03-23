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

使用アーキテクチャ: MVVM, Clean Architecture<br>
使用ツール: Ktlint<br>

## 工夫した点

プロジェクトの拡張性を重視し、特定のアーキテクチャと設計パターンを採用しました。<br>
それに加えて、DIを活用しモジュール間の結合度を下げ、メンテナンスを容易にしました。<br>
UIに関しては、Column, Row, BoxではModifierでpaddingをつけるが、それ以外のUIに関しては、Spacerを用いてpaddingを設定することにした。<br>
これにより、別々のUIで柔軟なpaddingを指定することができるようになりました。<br>

## アプリを使用する際の注意点

Supabaseを使用するので、local.propertyファイルに以下のコードを記述してください。

SUPABASE_URL=https://twxdngcbimgolvfbglpk.supabase.co <br>
SUPABASE_PUBLIC_KEY= Twitter(新X)のDMでキープリーズみたいな感じで言ってくだされば渡します。(@segnities007)
