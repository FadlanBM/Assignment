@extends('layouts.employees')
@section('content_admin')
    <div class="w-full p-6">
        <div class="bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black/5">
            <div class="flex justify-start gap-10">
                <div class="mr-5 p-0">
                    <form method="post" action="{{ route('lending.input.post') }}" id="inputField2"
                        enctype="multipart/form-data" class="flex justify-start gap-5 items-center flex-wrap">
                        @csrf
                        <!-- Bagian Formulir -->
                        <div class="flex-grow">
                            <label class="block text-gray-700 text-sm font-bold mb-2">
                                QR Book Add Borrower
                            </label>
                            <div class="flex items-center">
                                <input type="text" id="codeadd" name="codeadd" required
                                    class="shadow appearance-none border rounded w-full py-2 px-3 mr-2 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                                <i class='bx bx-qr' id="btn_scan_peminjam" style='font-size: 3em;'></i>
                            </div>
                        </div>
                        <div class="mt-6">
                            <button
                                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
                                Submit
                            </button>
                        </div>
                    </form>
                </div>
                <!-- Formulir kedua dan tombol Submit -->
                <div class="mr-5 p-0">
                    <form method="post" action="{{ route('lending.return.post') }}" id="inputField2"
                        enctype="multipart/form-data" class="flex justify-start gap-5 items-center flex-wrap">
                        @csrf
                        <!-- Bagian Formulir -->
                        <div class="flex-grow">
                            <label class="block text-gray-700 text-sm font-bold mb-2">
                                QR Book Return Validation
                            </label>
                            <div class="flex items-center">
                                <input type="text" id="codereturn" name="codereturn" required
                                    class="shadow appearance-none border rounded w-full py-2 px-3 mr-2 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                                <i class='bx bx-qr' id="btn_scan_return" style='font-size: 3em;'></i>
                            </div>
                        </div>
                        <div class="mt-6">
                            <button
                                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
                                Submit
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="overflow-x-auto mt-10">
                <table class="table">
                    <!-- head -->
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Borrower Name</th>
                            <th>Borrowing Date</th>
                            <th>Return Plan</th>
                            <th>Return date</th>
                            <th>Status</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        @foreach ($lendings as $ls)
                            <tr>
                                <td>
                                    {{ $loop->iteration }}
                                </td>
                                <td>
                                    {{ $borrower->name }}
                                </td>
                                <td>
                                    {{ $dateLast = $ls->borrow_date ? \Carbon\Carbon::parse($ls->borrow_date)->format('d-m-Y') : '-' }}
                                </td>
                                <td>{{ $dateLast = $ls->return_date ? \Carbon\Carbon::parse($ls->return_date)->format('d-m-Y') : '-' }}
                                </td>
                                <td>{{ $dateLast = $ls->date_last ? \Carbon\Carbon::parse($ls->date_last)->format('d-m-Y') : '-' }}
                                <td>{{ $ls->status == 'true' ? 'Active' : 'Not Active' }}
                                <td>
                                    <div class="flex justify-end gap-4">

                                        <a href="{{ route('lending.get', $ls->code) }}" id="show-buku">
                                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" class="h-6 w-6a">
                                                <circle cx="12" cy="12" r="10" fill="#000000" />
                                                <circle cx="12" cy="12" r="6" fill="#ffffff" />
                                            </svg>
                                        </a>

                                        <a x-data="{ tooltip: 'Delete' }" href="#"
                                            x-on:click.prevent="
            Swal.fire({
                title: 'Apakah Anda yakin?',
                text: 'Anda tidak akan dapat mengembalikan tindakan ini!',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Ya, Delete data!',
                cancelButtonText: 'Batal'
            }).then((result) => {
                if (result.isConfirmed) {
                     axios.delete(`/employees/delete/{{$ls->id}}`)
                         .then((response) => {
                        Swal.fire(
                            'Berhasil!',
                            'Berhasil delete data.',
                            'success'
                            ).then((result) => {
                                if (result.isConfirmed) {
                                    location.reload();
                                }
                            });
                        })
                        .catch((error) => {
                            Swal.fire(
                                'Gagal!',
                            'Terjadi kesalahan saat memberikan akses ke akun.',
                            'error'
                        );
                    });
                }
            });
        ">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                                stroke-width="1.5" stroke="currentColor" class="h-6 w-6"
                                                x-tooltip="tooltip">
                                                <path stroke-linecap="round" stroke-linejoin="round"
                                                    d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                                            </svg>
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        @endforeach
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <dialog id="my_modal_pinjam" class="modal">
        <div class="modal-box w-11/12 max-w-5xl">
            <h3 class="font-bold text-lg">Scan QR Ladings!</h3>
            <p class="py-4">Scan the QR Below</p>
            <div id="reader2" width="600px"></div>
            <div class="modal-action">
                <form method="dialog">
                    <button class="btn">Close</button>
                </form>
            </div>
        </div>
    </dialog>

    <dialog id="my_modal_return" class="modal">
        <div class="modal-box w-11/12 max-w-5xl">
            <h3 class="font-bold text-lg">Scan QR Ladings!</h3>
            <p class="py-4">Scan the QR Below</p>
            <div id="reader" width="600px"></div>
            <div class="modal-action">
                <form method="dialog">
                    <button class="btn">Close</button>
                </form>
            </div>
        </div>
    </dialog>

    <script>
        var modalQrPeminjam = document.getElementById('my_modal_pinjam');
        var modalQrReturn = document.getElementById('my_modal_return');
        var btn_scan_peminjam = document.getElementById('btn_scan_peminjam');
        var btn_scan_return = document.getElementById('btn_scan_return');

        btn_scan_peminjam.addEventListener('click', function() {
            scanQRCode('reader2');
            modalQrPeminjam.showModal();
        });

        btn_scan_return.addEventListener('click', function() {
            scanQRCodeReturn('reader');
            modalQrReturn.showModal();

        });

        function scanQRCodeReturn(readerId) {
            let html5QrcodeScanner1 = new Html5QrcodeScanner(
                readerId, {
                    fps: 10,
                    qrbox: {
                        width: 250,
                        height: 250
                    }
                },
                /* verbose= */
                false);

            function onScanSuccess(decodedText, decodedResult) {
                console.log(`Code matched = ${decodedText}`, decodedResult);
                window.location.href = `/employees/return/lending/${decodedText}`;
            }

            function onScanFailure(error) {
                console.warn(`Code scan error = ${error}`);
            }

            html5QrcodeScanner1.render(onScanSuccess, onScanFailure);
        }

        function scanQRCode(readerId) {
            let html5QrcodeScanner = new Html5QrcodeScanner(
                readerId, {
                    fps: 10,
                    qrbox: {
                        width: 250,
                        height: 250
                    }
                },
                /* verbose= */
                false);

            function onScanSuccess(decodedText, decodedResult) {
                console.log(`Code matched = ${decodedText}`, decodedResult);
                window.location.href = `/employees/show/validasi/lending/${decodedText}`;
            }

            function onScanFailure(error) {
                console.warn(`Code scan error = ${error}`);
            }

            html5QrcodeScanner.render(onScanSuccess, onScanFailure);
        }
    </script>
@endsection
