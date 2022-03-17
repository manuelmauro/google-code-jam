use std::io;

fn solve(mut c: Vec<isize>) -> usize {
    let mut cost = 0;
    for _ in 0..c.len() - 1 {
        let (index_min, _) = c.iter().enumerate().min_by(|a, b| a.1.cmp(&b.1)).unwrap();
        cost += index_min + 1;
        c.remove(index_min);
        c[0..index_min].reverse();
    }

    cost
}

fn main() {
    let mut t = String::new();
    io::stdin().read_line(&mut t).expect("Failed to read line");

    let t: isize = t.trim().parse().unwrap();

    for c in 0..t {
        let mut n = String::new();
        io::stdin().read_line(&mut n).expect("Failed to read line");

        let mut l = String::new();
        io::stdin().read_line(&mut l).expect("Failed to read line");

        let l: Vec<isize> = l.split_whitespace().map(|e| e.parse().unwrap()).collect();

        println!("Case #{}: {:?}", c + 1, solve(l));
    }
}
