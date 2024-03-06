<?php

namespace App\Http\Controllers;

use App\Models\Book;
use App\Models\BookCategory;
use App\Models\Categories;
use App\Models\Collection;
use Illuminate\Http\Request;
use Illuminate\Routing\Controller;
use Illuminate\Support\Facades\Crypt;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Str;

class BookManagementController extends Controller
{
    /**
     * Display a listing of the resource.
     */
    public function index()
    {
        $book = Book::all();
        $categories = Categories::all();
        return view('pages.employees.BookManagement', ['book' => $book, 'category' => $categories]);
    }

    /**
     * Show the form for creating a new resource.
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     */
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'title' => ['required', 'string', 'max:255'],
            'author' => ['required', 'string', 'max:255'],
            'publisher' => ['required', 'string', 'max:255'],
            'description' => ['required', 'string', 'max:255'],
            'stock' => ['required', 'numeric', 'min:0'],
            'code' => ['string', 'max:255'],
            'year_published' => 'required|integer|min:1900|max:' . date('Y'),
            'items' => 'required|array|min:1',
            'img' => 'required|image|mimes:jpeg,png,jpg,gif|max:2048',
        ]);

        if ($validator->fails()) {
            return redirect()->route('employees.book')->withErrors($validator)->withInput();
        }

        $book = new Book();
        $book->title = $request->title;
        $book->author = $request->author;
        $book->publisher = $request->publisher;
        $book->description = $request->description;
        $book->year_published = $request->year_published;
        $book->code = $request->code;
        $book->stock = $request->stock;
        if ($request->hasFile('img')) {
            $image = $request->file('img');
            $imageName = time() . '.' . $image->getClientOriginalExtension();
            $path = Storage::putFileAs('sampul', $image, $imageName);
            $img_file = basename($path);
            $book->img = $img_file;
        }
        $book->save();

        $category_id = $request->input('items', []);

        foreach ($category_id as $category_id) {
            $listcategory = new BookCategory();
            $listcategory->books_id = $book->id;
            $listcategory->categories_id = $category_id;
            $listcategory->save();
        }

        return back()->with('success', 'Berhasil menambahkan data buku');
    }

    /**
     * Display the specified resource.
     */
    public function show(string $id)
    {
    }

    /**
     * Show the form for editing the specified resource.
     */
    public function edit(string $id)
    {
        $decryptedID = Crypt::decryptString($id);
        $book = Book::findOrFail($decryptedID);
        $categories = Categories::all();
        $bookCategories = $book->categories->pluck('id')->toArray();
        return view('pages.employees.form.UpdateBuku', ['book' => $book, 'categories' => $categories, 'bookCategories' => $bookCategories]);
    }

    /**
     * Update the specified resource in storage.
     */
    public function update(Request $request, string $id)
    {
        $validator = Validator::make($request->all(), [
            'title' => ['required', 'string', 'max:255'],
            'author' => ['required', 'string', 'max:255'],
            'publisher' => ['required', 'string', 'max:255'],
            'description' => ['required', 'string', 'max:255'],
            'stock' => ['required', 'numeric', 'min:0'],
            'code' => ['string', 'max:255'],
            'year_published' => 'required|numeric',
            'items' => 'required|array|min:1',
            'img' => 'image|mimes:jpeg,png,jpg,gif|max:2048',
        ]);

        if ($validator->fails()) {
            return redirect()->route('employees.show', $id)->withErrors($validator)->withInput();
        }

        $book = Book::findOrFail($id);
        $book->title = $request->title;
        $book->author = $request->author;
        $book->publisher = $request->publisher;
        $book->description = $request->description;
        $book->year_published = $request->year_published;
        $book->code = $request->code;
        $book->stock = $request->stock;

        if ($request->hasFile('img')) {
            Storage::delete('sampul/' . $book->img);
            $image = $request->file('img');
            $imageName = time() . '.' . $image->getClientOriginalExtension();
            $path = Storage::putFileAs('sampul', $image, $imageName);
            $img_file = basename($path);
            $book->img = $img_file;
        }
        $book->save();
        $book->categories()->detach();
        $category_ids = $request->input('items', []);
        $book->categories()->attach($category_ids);

        return redirect()->route('employees.book')->with('success', 'Berhasil update data');
    }

    /**
     * Remove the specified resource from storage.
     */
    public function destroy(string $id)
    {
        $book = Book::findOrFail($id);
        $collection = Collection::where('books_id', $book->id)->first();

        if ($book) {
            $book->categories()->detach();
            if ($book->img && Storage::exists('sampul/' . $book->img)) {
                Storage::delete('sampul/' . $book->img);
            }
            if ($collection) {
                $collection->delete();
            }
            $book->delete();
            return response()->json([], 200);
        } else {
            return response()->json(['message' => 'Buku tidak ditemukan'], 404);
        }
    }
}
