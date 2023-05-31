package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/*read me
 *
 * ・2次元配列を使ってインディアンポーカーゲームを作成します。
 *
 * ・クラスは使いません。
 *
 */


public class IndianPoker {

    static String[] marks = { "スペード　", "ハート　　", "ダイヤ　　", "クラブ　　" };
    static int[][] cards = new int[4][13];
    static int[] cpuCard;
    static int[] myCard;
    static int cpuPoint = 0;
    static int myPoint = 0;
    static int changeCount;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        int gameCount = 1;

        System.out.println("【ゲームスタート】");

        setCards();

        while (gameCount <= 3) {

            changeCount=2;

            System.out.println("★" + gameCount + "回戦");
            System.out.println("山札から1枚引きます");

            cpuCard = choiceCard();
            myCard = choiceCard();

            System.out.println("相手の表示札：" + display(cpuCard));

            changeCard();

            System.out.println("【勝負!】");
            System.out.println("相手の表示札：" + display(cpuCard));
            System.out.println("自分の表示札：" + display(myCard));

            if (myCard[1] > cpuCard[1]) {
                System.out.println("あなたの勝ちです!");
                myPoint++;
            } else if (myCard[1] < cpuCard[1]) {
                System.out.println("あなたの負けです!");
                cpuPoint++;
            } else {
                System.out.println("引き分けです!");
            }

            System.out.println("\n\n\n捨札");
            System.out.println("-----------------------------------------------------");
            for (int i = 0; i < 4; i++) {
                System.out.print("\n" + marks[i] + "| ");
                for (int j = 0; j < 13; j++) {
                    if (cards[i][j] == 0) {
                        int[] droppedCard = { i, j };
                        System.out.print(display(droppedCard).substring(display(droppedCard).length() - 2)+" ");
                    }
                }
            }
            System.out.println("\n\n-----------------------------------------------------");
            System.out.println("勝利点  あなた" + myPoint + ":" + "相手" + cpuPoint + "\n\n\n");

            gameCount++;
        }

        System.out.println("【最終結果】");
        if (myPoint > cpuPoint) {
            System.out.println("あなたの勝ちです!");
        } else if (myPoint < cpuPoint) {
            System.out.println("あなたの負けです!");
        } else {
            System.out.println("引き分けです。");
        }
    }

    //トランプを用意
    static void setCards() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                cards[i][j] = j + 1;
            }
        }
    }

    //まだ捨てられていないカードから1枚を選択
    //値が0になっているカードが使用済みカード
    static int[] choiceCard() {
        while (true) {
            int randomMark = new Random().nextInt(4);
            int randomNumber = new Random().nextInt(13);

            if (cards[randomMark][randomNumber] != 0) {
                cards[randomMark][randomNumber] = 0;

                return new int[]{ randomMark, randomNumber };
            }
        }
    }

    //基本intだけで管理しているため、表示用に変換する関数
    static String display(int[] card) {
        if (card[1] < 9) {
            return marks[card[0]] + ((card[1] + 2));
        } else {
            return switch (card[1]) {
                case 9 -> marks[card[0]] + "J";
                case 10 -> marks[card[0]] + "Q";
                case 11 -> marks[card[0]] + "K";
                case 12 -> marks[card[0]] + "A";
                default -> "";
            };
        }
    }

    //カードを交換する。
    static void changeCard() throws IOException {
        while (changeCount > 0) {
            System.out.println("\n\nあなたの手札を交換しますか?" + "(残" + changeCount + "回)");
            System.out.print("1:交換\n2:勝負\n>");
            String str = br.readLine();
            int decision = Integer.parseInt(str);

            if (decision == 1) {
                System.out.println("あなたの手札：" + display(myCard) + " でした");
                System.out.println("山札から1枚引きます");
                changeCount--;
                myCard = choiceCard();

            } else {
                break;
            }
        }
    }
}