var Frame = require('./Frame');

function SpecialFrame(rolls) {
    this.rolls = rolls;
}
SpecialFrame.prototype = new Frame();
SpecialFrame.prototype.roll = function (val) {
    if (this.rolls.length < 3) {
        this.rolls.push(val);
        this.isComplete = true;
    }
};


module.exports = exports = SpecialFrame;
