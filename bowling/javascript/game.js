var _ = require('lodash');
var Frame = require('./Frame');
var SpecialFrame = require('./SpecialFrame');

var MAX_FRAMES = 10, MAX_PINS = 10;


function Game() {
    this.frames = [];
}

Game.prototype = {

    roll: function (knockedDown) {
        _.each(this.frames, function (frame) {
            frame.roll(knockedDown);
        });

        if (this.frames.length === MAX_FRAMES) {
            return;
        }

        this.inProgressFrame = this.inProgressFrame || new Frame();
        this.inProgressFrame.roll(knockedDown);

        if (this.inProgressFrame.getScore() === MAX_PINS) {
            this.frames.push(new SpecialFrame(this.inProgressFrame.rolls));
            this.inProgressFrame = null;
        } else if (this.inProgressFrame.rolls.length === 2) {
            this.frames.push(this.inProgressFrame);
            this.inProgressFrame = null;
        }

    },

    score: function () {
        return _.reduce(this.frames, function (acc, frame) {
            return acc + frame.getScore();
        }, 0);
    }

};

module.exports = exports = Game;
