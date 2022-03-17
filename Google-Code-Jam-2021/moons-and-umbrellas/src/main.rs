use std::io;

fn solve(x: isize, y: isize, s: String) -> isize {
    let s = s.replace("?", "");

    let mut cost: isize = 0;

    let mut ahead = s.chars();
    ahead.next();

    for (fst, snd) in s.chars().zip(ahead) {
        if fst == 'C' && snd == 'J' {
            cost += x;
        } else if fst == 'J' && snd == 'C' {
            cost += y;
        }
    }

    cost
}

fn main() {
    let mut t = String::new();
    io::stdin().read_line(&mut t).expect("Failed to read line");

    let t: isize = t.trim().parse().unwrap();

    for c in 0..t {
        let mut line = String::new();
        io::stdin()
            .read_line(&mut line)
            .expect("Failed to read line");

        let line: Vec<&str> = line.trim().split_whitespace().collect();

        let x: isize = line[0].parse().unwrap();
        let y: isize = line[1].parse().unwrap();
        let s: String = line[2].into();

        println!("Case #{}: {}", c + 1, solve(x, y, s));
    }
}
