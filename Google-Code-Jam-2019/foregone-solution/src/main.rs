use std::io;

fn test_set_1(n: String) -> (String, String) {
    let mut a = String::new();
    let mut b = String::new();

    for c in n.trim().chars() {
        if c == '4' {
            a.push('3');
            b.push('1');
        } else {
            a.push(c);
            b.push('0');
        }
    }

    (a, b)
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

        let (a, b) = test_set_1(n);
        print!("Case #{}: {} {}\n", case + 1, a, b);
    }
}
