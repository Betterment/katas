var _ = require('lodash');

function Frame() {
    this.rolls = [];
}
Frame.prototype.getScore = function () {
    return _.reduce(this.rolls, function (acc, roll) { return acc + roll; });
};
Frame.prototype.roll = function (val) {
    if (this.rolls.length < 2) {
        this.rolls.push(val);
    }
};

module.exports = exports = Frame;
