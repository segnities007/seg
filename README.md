# SEG

## 概要

Segは、テキストコミュニケーションに特化したSNSアプリです。
従来のSNSアプリは、画像や動画などのメディア投稿が主流となっていますが、
Segではあえてこれらのメディア機能を排除し、言葉による表現に焦点を当てています。
プログラミングを学ぶ中で、言語表現の価値を再認識した経験から、
ユーザーが言葉を通じて自身の考えやアイデアを世界に発信できるプラットフォームを提供したいと考え、Segを開発しました。

## 技術スタック

クライアント: Kotlin, Jetpack Compose
サーバーサイド: Supabase

使用アーキテクチャ: MVVM, Clean Architecture
使用ツール: Ktlint
設計パターン等: (誤り・不正確な説明あり)
        UIコンポーネント設計パターン: Slot Pattern, 
                                　Compound Component Pattern
        UIの状態管理に関する設計パターン: UDF (単方向データフロー)

## 工夫した点

プロジェクトの拡張性を重視し、特定のアーキテクチャと設計パターンを採用しました。
それに加えて、DIを活用しモジュール間の結合度を下げ、メンテナンスを容易にしました。
UIに関しては、Column, Row, BoxではModifierでpaddingをつけるが、それ以外のUIに関しては、Spacerを用いてpaddingを設定することにした。
これにより、別々のUIで柔軟なpaddingを指定することができるようになりました。

## アプリを使用する際の注意点

Supabaseを使用するので、local.propertyファイルに以下のコードを記述してください。

SUPABASE_URL=https://twxdngcbimgolvfbglpk.supabase.co <br>
SUPABASE_PUBLIC_KEY= Twitter(新X)のDMでキープリーズみたいな感じで言ってくだされば渡します。(@segnities007)
