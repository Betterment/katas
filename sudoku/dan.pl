#!/usr/bin/perl

use strict;
use List::Util qw(shuffle);

my $file = $ARGV[0];

open(PUZZLE, "puzzles/$file.txt");
local $/;
my $puzzle = <PUZZLE>;
close(PUZZLE);

my ($unsolved, $solved) = split("\n", $puzzle);
chomp $unsolved; chomp $solved;
my @chars = split('', $unsolved);

sub get_rows {
    my @rows;
    $rows[int($_ / 9)] .= $chars[$_] for (0 .. $#chars);
    return @rows;
}

sub strip_dots {
    my @rows = get_rows;
    foreach my $i (0 .. $#rows) {
        $rows[$i] =~ s/\.//g;
    }
    return @rows;
}

my @rowcopy = get_rows;
my @justnums = strip_dots;

sub try_rand {
    my @rows = @rowcopy;
    my $n;
    foreach my $i (0 .. $#rows) {
        my $shuffled = join('', shuffle 1..9);
        foreach my $x (split('', $justnums[$i])) { $shuffled =~ s/$x//; }
        my @nums = split('', $shuffled);
        $rows[$i] =~ s/\./$n/ while ($rows[$i] =~ /\./ and $n = shift @nums);
    }
    my $try = join('', @rows);
    #print "Trying $try\n";
    return $try;
}

my $try;

$try = try_rand until $try eq $solved;

print "Solved!\n$try";
