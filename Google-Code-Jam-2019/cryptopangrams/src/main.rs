extern crate num_bigint;
extern crate num_traits;

use std::collections::{BTreeSet, HashMap};
use std::io;

use num_bigint::BigUint;

fn test_set_1(cypher_text: Vec<BigUint>) -> String {

    let snd_char = mcd(&cypher_text[0], &cypher_text[1]);
    let fst_char = &cypher_text[0] / snd_char.clone();

    let intermediate_text = unzip(fst_char, cypher_text);
    let stele = stele_from(&intermediate_text);
    let plain_text = translate(&intermediate_text, stele);

    plain_text
}

fn unzip(fst_char: BigUint, cypher_text: Vec<BigUint>) -> Vec<BigUint> {
    let mut plain_text = Vec::new();
    plain_text.push(fst_char.clone());

    let mut cursor = fst_char;
    for e in cypher_text {
        plain_text.push(&e / &cursor);
        cursor = e / cursor;
    }

    plain_text
}

fn stele_from(intermediate_text: &Vec<BigUint>) -> HashMap<BigUint, char> {
    let mut enc_alphabet = BTreeSet::new();
    for enc_letter in intermediate_text {
        enc_alphabet.insert(enc_letter.clone());
    }

    let mut stele = HashMap::new();
    for (i, l) in enc_alphabet.into_iter().enumerate() {
        stele.insert(l, "ABCDEFGHIJKLMNOPQRSTUVWXYZ".chars().nth(i).unwrap());
    }

    stele
}

fn translate(intermediate_text: &Vec<BigUint>, stele: HashMap<BigUint, char>) -> String {
    let mut string = String::new();
    for c in intermediate_text {
        string.push(*stele.get(&c).unwrap());
    }

    string
}

fn mcd(x: &BigUint, y: &BigUint) -> BigUint {
    let mut a = x.clone();
    let mut b = y.clone();

    while a != b {
        if a > b {
            a -= &b;
        } else {
            b -= &a;
        }
    }

    a
}

fn main() {
    let mut t = String::new();
    io::stdin().read_line(&mut t)
        .expect("Failed to read line");

    let t: usize = t.trim().parse().unwrap();

    for case in 0..t {
        let mut n = String::new();
        io::stdin().read_line(&mut n)
            .expect("Failed to read line");

        let mut cypher_text = String::new();
        io::stdin().read_line(&mut cypher_text)
            .expect("Failed to read line");

        let cypher_text: Vec<BigUint> = cypher_text
            .split_whitespace()
            .map(|tok| tok.parse().unwrap())
            .collect();

        let path = test_set_1(cypher_text);
        print!("Case #{}: {}\n", case + 1, path);
    }
}
