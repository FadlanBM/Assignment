<?php

namespace App\Http\Controllers;

use App\Models\Book;
use App\Models\Borrowers;
use App\Models\Categories;
use App\Models\Lendings;
use Illuminate\Http\Request;
use Illuminate\Routing\Controller;

class EmployeesIndexController extends Controller
{
    public function index()
    {
        $countBorrowers = Borrowers::where('active', true)->count();
        $bookCount = Book::count();
        $categoryCount = Categories::count();
        $lendingsTrue = Lendings::where('status', true)->count();
        $lendingsFalse = Lendings::where('status', false)->count();
        $lendingsReturn = Lendings::whereNotNull('date_last')->count();

        return view('pages.employees.index', [
            'countBorrowers' => $countBorrowers,
            'bookCount' => $bookCount,
            'categoryCount' => $categoryCount,
            'lendingsTrue' => $lendingsTrue,
            'lendingsFalse' => $lendingsFalse,
            'lendingsReturn' => $lendingsReturn,
        ]);
    }
}
