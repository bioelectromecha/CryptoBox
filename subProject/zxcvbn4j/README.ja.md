
# zxcvbn4j [![Build Status](https://travis-ci.org/nulab/zxcvbn4j.svg?branch=master)](https://travis-ci.org/nulab/zxcvbn4j) [![Coverage Status](https://coveralls.io/repos/nulab/zxcvbn4j/badge.svg?branch=master&service=github)](https://coveralls.io/github/nulab/zxcvbn4j?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.nulab-inc/zxcvbn/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.nulab-inc/zxcvbn)

zxcvbn4j は、JavaScriptのパスワード強度ジェネレータである[zxcvbn](https://github.com/dropbox/zxcvbn)をJavaにポーティングしたものです。

## 更新

以下のバージョンは[zxcvbn 4.3.0](https://github.com/dropbox/zxcvbn/releases/tag/4.3.0)をポーティング

* 2016/10/01 1.1.6 リリース.
* 2016/09/27 1.1.5 リリース.
* 2016/07/08 1.1.4 リリース.
* 2016/05/27 1.1.3 リリース.
* 2016/05/25 1.1.2 リリース.
* 2016/03/19 1.1.1 リリース.
* 2016/03/06 1.1.0 リリース.

以下のバージョンは[zxcvbn 4.2.0](https://github.com/dropbox/zxcvbn/releases/tag/4.2.0)をポーティング

* 2016/01/28 1.0.2 リリース.
* 2016/01/27 1.0.1 リリース.
* 2015/12/24 1.0.0 リリース.

## 特別な機能

* 隣接したキー配列の照合処理にJISキーボードを対応。
* フィードバックメッセージのローカライズに対応。

## インストール

### gradle を利用する場合

```
compile 'com.nulab-inc:zxcvbn:1.1.6'
```

### maven を利用する場合

```
<dependency>
  <groupId>com.nulab-inc</groupId>
  <artifactId>zxcvbn</artifactId>
  <version>1.1.6</version>
</dependency>
```

## ビルド

ビルド方法:

```
$ git clone git@github.com:nulab/zxcvbn4j.git
$ cd zxcvbn4j/
$ ./gradlew build
```

## 使い方

基本的な使い方です。Androidも同じようにご利用できます。

``` java
Zxcvbn zxcvbn = new Zxcvbn();
Strength strength = zxcvbn.measure("This is password");
```

独自の辞書を追加したい場合は、第二引数にリスト<文字列>のタイプのキーワード一覧を渡します。

``` java
List<String> sanitizedInputs = new ArrayList();
sanitizedInputs.add("nulab");
sanitizedInputs.add("backlog");
sanitizedInputs.add("cacoo");
sanitizedInputs.add("typetalk");

Zxcvbn zxcvbn = new Zxcvbn();
Strength strength = zxcvbn.measure("This is password", sanitizedInputs);
```

返却する結果は、"Strength"インスタンスです。[zxcvbn](https://github.com/dropbox/zxcvbn) が返却する結果とほぼ同じものです。

```
# パスワードの「乱雑さ」「複雑さ」を表す指標
strength.guesses
strength.guessesLog10

# いくつかのシナリオに基づいたクラック時間の推測
strength.crackTimeSeconds
{
  # オンライン攻撃でパスワード認証に回数制限が有る場合
  onlineThrottling100PerHour

  # オンライン攻撃でパスワード認証に回数制限が無い場合
  onlineNoThrottling10PerSecond

  # オフライン攻撃で、bcrypt、scrypt、PBKDF2等を使ってハッシュ化している場合
  offlineSlowHashing1e4PerSecond

  # オフライン攻撃で、SHA-1、SHA-256またはMD5等を使ってハッシュ化している場合
  offlineFastHashing1e10PerSecond
}

# strength.crackTimeSecondsを表示するために文字列化した値(秒未満、3時間、世紀 等)
strength.crackTimeDisplay


# 0から4の整数
# 0 弱い       （guesses < ^ 3 10）
# 1 やや弱い    （guesses <^ 6 10）
# 2 普通       （guesses <^ 8 10）
# 3 強い       （guesses < 10 ^ 10）
# 4 とても強い   （guesses >= 10 ^ 10）
strength.score

# 安全なパスワード作成に役立つフィードバック。(score <= 2 のみ表示)
{
  # 警告文
  warning

  # 提案
  suggestions
}

# 測定に用いたパターンのリスト
strength.sequence

# 測定にかかった時間
strength.calc_time
```

## フィードバックメッセージのローカライズ

zxcvbn4jは英語で返却されるフィードバックメッセージを他の言語に変更可能です。

``` java
// パスワード強度を測定して Strength を取得します。
Zxcvbn zxcvbn = new Zxcvbn();
Strength strength = zxcvbn.measure("This is password");

// 事前に用意したプロパティファイル(※)の名前とロケールを指定して、ResourceBundle を取得します。
ResourceBundle resourceBundle = ResourceBundle.getBundle("This is bundle name", Locale.JAPAN);

// FeedbackにResourceBundleを渡して、ローカライズされたFeedbackを生成します。
Feedback feedback = strength.getFeedback();
Feedback localizedFeedback = feedback.withResourceBundle(resourceBundle);

// getSuggestions()、getWarning()で取得するフィードバックメッセージはローカライズ済みです。
List<String> localizedSuggestions = localizedFeedback.getSuggestions();
String localizedWarning = localizedFeedback.getWarning();
```

プロパティファイルに定義するキーとメッセージは、[messages.properties](https://github.com/nulab/zxcvbn4j/blob/master/src/main/resources/com/nulabinc/zxcvbn/messages.properties) を参考に作成してください。

## バグ報告やご意見

バグ報告, ご意見、ご質問等は [Github Issues](https://github.com/nulab/zxcvbn4j/issues) にお願い致します。

## ライセンス

MIT License

* http://www.opensource.org/licenses/mit-license.php

## 要件

* Java 1.7以上

## このライブラリを使用しているアプリケーション

- [Cacoo](https://cacoo.com/)
- [Typetalk](https://typetalk.in/)
- [JetBrains Hub](https://www.jetbrains.com/hub/)
