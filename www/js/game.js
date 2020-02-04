// enchant();

// window.onload = function() {
//   var game = new Game(320, 320);
//   game.spriteSheetWidth = 304; //Width of the sprite sheet we will be using
//   game.spriteSheetHeight = 16; //Height of our sprite sheet
//   game.itemSpriteSheetWidth = 64; //The width of the sprite sheet for our items
//   game.fps = 20;
//   game.preload('chara1.png', 'chara1.gif');
//   game.onload = function() {
//     var map = new Map(32, 32);
//     map.image = game.assets['chara1.gif'];
//     map.loadData([
//       [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
//       [4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 4],
//       [4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3],
//       [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4],
//       [4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3],
//       [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4],
//       [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4],
//       [4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 4],
//       [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4],
//       [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4],
//       [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4],
//       [4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4],
//       [4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4],
//       [4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4],
//       [4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4],
//       [4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4],
//       [5, 6, 6, 6, 6, 4, 4, 4, 5, 5, 5, 4, 3, 4, 4, 4, 4, 4, 4, 4, 4],
//       [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5],
//       [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5]
//     ]);
  
//     game.rootScene.addChild(map);
//   };

//   game.start();
// };;
enchant();

// ここで自作クラスBearをつくる
Bear = Class.create(Sprite, // Spriteクラスを継承
{
    initialize: function(x, y) { //初期化する
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
        if (this.frame == 2) //もし、クマさんのframeが2なら
        this.frame = 0; //frameを0にリセットするよ
        this.frame++;
    },
});

window.onload = function() {
    game = new Game(420, 420);
    game.preload('chara1.gif');
    game.onload = function() {
        //↓これはforループ(フォーループ)といって、{}で囲まれた処理を繰り返すよ
        for (a = 0; a < 5; a++) { //5回繰り返す 
            bear = new Bear(10, a * 32); //aには0から4までの値が順番に入る
        }
    }
    game.start();
}