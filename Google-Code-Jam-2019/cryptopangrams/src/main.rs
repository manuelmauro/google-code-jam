extern crate num_bigint;
extern crate num_traits;

use std::collections::{BTreeSet, HashMap};
use std::io;

use num_bigint::BigUint;

fn test_set_1(cypher_text: Vec<BigUint>) -> String {
    // decode first and second primes
    let snd_char = mcd(&cypher_text[0], &cypher_text[1]);
    let fst_char = &cypher_text[0] / snd_char.clone();

    // unzip cypher text
    let mut intermediate_text = Vec::new();
    intermediate_text.push(fst_char.clone());

    let mut cursor = fst_char;
    for e in cypher_text {
        intermediate_text.push(&e / &cursor);
        cursor = e / cursor;
    }

    // compute the stele
    let mut enc_alphabet = BTreeSet::new();
    for enc_letter in &intermediate_text {
        enc_alphabet.insert(enc_letter.clone());
    }

    let mut stele = HashMap::new();
    for (i, l) in enc_alphabet.into_iter().enumerate() {
        stele.insert(l, "ABCDEFGHIJKLMNOPQRSTUVWXYZ".chars().nth(i).unwrap());
    }

    // decrypt the text
    let mut plain_text = String::new();
    for c in intermediate_text {
        plain_text.push(*stele.get(&c).unwrap());
    }

    plain_text
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
    io::stdin().read_line(&mut t).expect("Failed to read line");

    let t: usize = t.trim().parse().unwrap();

    for case in 0..t {
        let mut n = String::new();
        io::stdin().read_line(&mut n).expect("Failed to read line");

        let mut cypher_text = String::new();
        io::stdin()
            .read_line(&mut cypher_text)
            .expect("Failed to read line");

        let cypher_text: Vec<BigUint> = cypher_text
            .split_whitespace()
            .map(|tok| tok.parse().unwrap())
            .collect();

        let path = test_set_1(cypher_text);
        print!("Case #{}: {}\n", case + 1, path);
    }
}
