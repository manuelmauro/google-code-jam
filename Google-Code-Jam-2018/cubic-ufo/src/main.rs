use std::fmt;
use std::io;

extern crate nalgebra as na;
use na::{Point3, Real, Rotation3, Vector3};

struct CubicUfo {
    base: Vec<Point3<f32>>,
    vertices: Vec<Point3<f32>>,
}

impl CubicUfo {
    fn new() -> CubicUfo {
        let base = vec![
            Point3::new(0.5, 0.0, 0.0),
            Point3::new(0.0, 0.5, 0.0),
            Point3::new(0.0, 0.0, 0.5),
        ];

        let vertices = vec![
            Point3::new(0.5, 0.5, 0.5),
            Point3::new(-0.5, 0.5, 0.5),
            Point3::new(-0.5, 0.5, -0.5),
            Point3::new(0.5, 0.5, -0.5),
            Point3::new(0.5, -0.5, 0.5),
            Point3::new(-0.5, -0.5, 0.5),
            Point3::new(-0.5, -0.5, -0.5),
            Point3::new(0.5, -0.5, -0.5),
        ];

        CubicUfo { base, vertices }
    }

    fn apply_rotation(&mut self, rot: Rotation3<f32>) {
        for p in self.base.iter_mut() {
            *p = rot * *p;
        }

        for p in self.vertices.iter_mut() {
            *p = rot * *p;
        }
    }

    fn shadow(&self) -> f32 {
        let exagon = vec![
            self.vertices[4].clone(),
            self.vertices[5].clone(),
            self.vertices[1].clone(),
            self.vertices[2].clone(),
            self.vertices[3].clone(),
            self.vertices[7].clone(),
        ];

        shoelace(exagon)
    }
}


impl fmt::Display for CubicUfo {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(f, "{} {} {}\n{} {} {}\n{} {} {}\n",
            self.base[0].x,
            self.base[0].y,
            self.base[0].z,
            self.base[1].x,
            self.base[1].y,
            self.base[1].z,
            self.base[2].x,
            self.base[2].y,
            self.base[2].z,
        )
    }
}

fn shoelace(points: Vec<Point3<f32>>) -> f32 {
    let n = points.len();

    let mut first = 0.0;
    for i in 0..n - 1 {
        let x_i = points[i].x;
        let z_ip1 = points[i + 1].z;
        first += x_i * z_ip1;
    }

    let x_nm1 = points[n - 1].x;
    let z_0 = points[0].z;
    let n1 = x_nm1 * z_0;

    let mut second = 0.0;
    for i in 0..n - 1 {
        let x_ip1 = points[i + 1].x;
        let z_i = points[i].z;
        second += x_ip1 * z_i;
    }

    let x_0 = points[0].x;
    let z_nm1 = points[n - 1].z;
    let n1n = x_0 * z_nm1;

    0.5 * (first + n1 - second - n1n).abs()
}

fn find_angle(a: f32, ufo: &mut CubicUfo) -> f32 {
    const MAX_ANGLE: f32 = 0.95547971;

    let mut area = 1.0;

    let mut l = 0.0;
    let mut r = MAX_ANGLE;
    let mut angle = 0.0;

    while (area - a).abs() > 0.000001 {
        angle = ((r - l) / 2.0) + l;
        let axis = Vector3::z_axis();
        let z_rotation = Rotation3::from_axis_angle(&axis, angle);

        ufo.apply_rotation(z_rotation);

        area = ufo.shadow();

        if a > area {
            l = angle;
        } else {
            r = angle;
        }

        ufo.apply_rotation(Rotation3::from_axis_angle(&axis, -angle));
    }

    angle
}

// fn test_set_1(a: f32) -> Ufo {
//     let axis = Vector3::z_axis();
//     let angle = (1.0 / a).acos();
//
//     let rotation = Rotation3::from_axis_angle(&axis, angle);
//
//     let mut ufo = Ufo::new();
//
//     ufo.apply_rotation(rotation);
//
//     ufo
// }

fn test_set_2(a: f32) -> CubicUfo {
    let axis = Vector3::y_axis();
    let angle: f32 = Real::frac_pi_4();

    let y_rotation = Rotation3::from_axis_angle(&axis, angle);

    let mut ufo = CubicUfo::new();

    ufo.apply_rotation(y_rotation);

    let axis = Vector3::z_axis();
    let angle = find_angle(a, &mut ufo);

    let z_rotation = Rotation3::from_axis_angle(&axis, angle);

    ufo.apply_rotation(z_rotation);

    ufo
}

fn main() -> std::io::Result<()> {
    let mut n = String::new();
    io::stdin().read_line(&mut n)
        .expect("Failed to read line");

    let n: usize = n.trim().parse().unwrap();

    for case in 0..n {
        let mut a = String::new();
        io::stdin().read_line(&mut a)
            .expect("Failed to read line");
        let a: f32 = a.trim().parse().unwrap();

        // println!("{}", test_set_1(a));
        print!("Case #{}:\n{}", case + 1, test_set_2(a));
    }

    Ok(())
}
