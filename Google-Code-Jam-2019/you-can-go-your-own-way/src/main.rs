use std::io;

fn test_set_1(path_lydia: String) -> String {
    let mut path_me = String::new();

    for c in path_lydia.trim().chars() {
        match c {
            'E' => path_me.push('S'),
            'S' => path_me.push('E'),
             _  => (),
        }
    }

    path_me
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

        let mut path_lydia = String::new();
        io::stdin().read_line(&mut path_lydia)
            .expect("Failed to read line");

        let path = test_set_1(path_lydia);
        print!("Case #{}: {}\n", case + 1, path);
    }
}
