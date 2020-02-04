enchant();

// ここで自作クラスBearをつくる
 Bear = Class.create(
  Sprite, // Spriteクラスを継承
  {
    initialize: function(x, y) {
      //初期化する
      Sprite.call(this, 32, 32); //Spriteオブジェクトを初期化
      this.image = game.assets['chara1.gif'];
      this.x = x;
      this.y = y;
      this.frame = 0;
      game.rootScene.addChild(this);
    },
    //enterframeイベントのリスナーを定義する
    onenterframe: function() {
      if (this.age % 4 > 0) return; //遅くするおまじない
      //少し右へ移動する
      this.x++;

      //クマさんをアニメーションする
      if (this.frame == 2)
        //もし、クマさんのframeが2なら
        this.frame = 0; //frameを0にリセットするよ
      this.frame++;
    }
  }
);

